var express = require('express')
var cors = require('cors')
var app = express()

const AWS = require('aws-sdk');
const fs = require("fs");
const mime = require('mime');

const axios = require('axios');

const http = require('http');



multerS3 = require('multer-s3');

require('dotenv').config();

const multer = require('multer');


const bodyParser = require('body-parser');

// Parse JSON requests
app.use(bodyParser.urlencoded())

AWS.config.update({
    accessKeyId: process.env.CELLAR_ADDON_KEY_ID,
    secretAccessKey: process.env.CELLAR_ADDON_KEY_SECRET,
});
const s3 = new AWS.S3({ endpoint:  process.env.CELLAR_ADDON_HOST });


// Define your S3 bucket name and object key
const bucketName = 'polysnap';
const objectKey = 'verybeautiful.jpg';


app.use(cors());


app.post('/baseImage', function (req, res) {
    try{
        const imageFile = './beautiful.jpg';
        const imageBuffer = fs.readFileSync(imageFile);

        const fileContent = 'Hello, World!';
        const params = {
            Bucket: bucketName,
            Key: objectKey,
            Body: imageBuffer,
            Expires: 120,
            ACL: 'public-read',
        };
        console.log(`S3 Endpoint: ${process.env.CELLAR_ADDON_HOST}`);
        console.log('S3 Upload Params:', params);
        s3.upload(params, function (err, data) {
            if (err) {
                console.error("Error uploading image:", err);
                res.writeHead(500, { 'Content-Type': 'text/plain' });
                res.end('Error uploading image');
            } else {
                console.log("Image uploaded successfully:", data.Location);
                res.writeHead(200, { 'Content-Type': 'text/plain' });
                res.end('Image uploaded successfully');
            }
        });
        console.log(`File uploaded successfully to ${bucketName}/${objectKey}`);
    }
    catch (error) {
        console.error('Error uploading image:', error);
        res.status(500).send('Error uploading image');
    }
});

var upload = multer({
    storage: multerS3({
        s3: s3,
        acl: 'public-read',
        bucket: bucketName,
        key: function (req, file, cb) {
            var newFileName = Date.now() + '-' + file.originalname; // Add timestamp to the file name
            console.log(newFileName);
            cb(null, newFileName);
        }
    })
});

app.post('/upload', upload.array('image', 25), function (req, res) {
    // Set expiration policy for uploaded objects
    const expirationTime = new Date();
    expirationTime.setMonth(expirationTime.getMonth() + 1);

    // Get the uploaded object keys
    const objectKeys = req.files.map(file => file.originalname);

    // Set expiration policy for each object
    objectKeys.forEach(key => {
        const params = {
            Bucket: bucketName,
            Key: key,
            Expires: expirationTime
        };

        s3.putObject(params, function(err, data) {
            if (err) {
                console.error(err);
            } else {
                console.log(`Object ${key} will expire at ${expirationTime}`);
            }
        });
    });



    const lifespan = req.body.lifespan;
    const userId = req.body.userId; // Get userID from the request body
    const chatId = req.body.chatId; // Get chatID from the request body

    console.log('UserID:', userId);
    console.log('ChatID:', chatId);
    console.log(req.files);

    // Prepare data to be sent to the message publisher
    const data = JSON.stringify({
        message: req.files[0].location,
        userId: userId,
        chatId: chatId,
        lifespan: lifespan
    });
    const options = {
        hostname: process.env.POLY_PUBLISHER_HOST,
        port: 80,
        path: '/api/message-published/send-message',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Content-Length': data.length
        }
    };
    console.log('Option de la requête HTTP:')
    console.log(options);


    // Créer la requête HTTP POST
    const request = http.request(options, (response) => {
        let responseData = '';

        // Recevoir les données de la réponse du message publisher
        response.on('data', (chunk) => {
            responseData += chunk;
        });

        // Fin de la réponse du message publisher
        response.on('end', () => {
            console.log('Réponse du message publisher:', responseData);
            res.send({
                message: "Uploaded!",
                userId: userId,
                chatId: chatId,
                urls: req.files.map(function(file) {
                    return {url: file.location, name: file.key, type: file.mimetype, size: file.size};
                })
            });
        });
    });

    // Gérer les erreurs de la requête HTTP
    request.on('error', (error) => {
        console.error('Erreur lors de l\'envoi de la requête au message publisher:', error);
        res.status(500).send('Erreur interne du serveur');
    });

    // Envoyer les données dans la requête HTTP
    request.write(data);
    request.end();
});


async function postData(data, url) {
    const headers = {
        'Content-Type': 'application/json',
    };

    try {
        const response = await axios.post(url, data, { headers });
        return response.data;
    } catch (error) {
        // Handle error here
        console.error('Error:', error);
        throw error; // Re-throw the error for the caller to handle
    }
}

app.post('/uploadold', function (req, res) {
    try {
        console.log(req.body);
        console.log(req.image);
        if (!req.image) {
            return res.status(400).send('No image data found.');
        }
        const imageBuffer = req.image.buffer
        const mimeType = mime.getType(imageBuffer);
        const fileExtension = mime.getExtension(mimeType);
        const fileName = `${Date.now()}-${Math.random().toString(36).substring(7)}.${fileExtension}`;
        const params = {
            Bucket: bucketName,
            Key: fileName,
            Body: imageBuffer,
            ACL: 'public-read',
        };
        console.log(`S3 Endpoint: ${process.env.CELLAR_ADDON_HOST}`);
        console.log('S3 Upload Params:', params);
        s3.upload(params, function (err, data) {
            if (err) {
                console.error("Error uploading image:", err);
                res.writeHead(500, {'Content-Type': 'text/plain'});
                res.end('Error uploading image');
            } else {
                console.log("Image uploaded successfully:", data.Location);
                res.writeHead(200, {'Content-Type': 'text/plain'});
                res.end('Image uploaded successfully');
            }
        });
        console.log(`File uploaded successfully to ${bucketName}/${fileName}`);





    } catch (error) {
        console.error('Error uploading image:', error);
        res.status(500).send('Error uploading image');
    }
});

app.listen(8080, '0.0.0.0',  function () {
    console.log('CORS-enabled web server listening on port 8080')
})
