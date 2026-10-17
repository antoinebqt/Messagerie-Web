const { createServer, IncomingMessage, ServerResponse } = require('http');
const AWS = require('aws-sdk');
const fs = require("fs");
const mime = require("mime");
require('dotenv').config();

AWS.config.update({
    accessKeyId: process.env.CELLAR_ADDON_KEY_ID,
    secretAccessKey: process.env.CELLAR_ADDON_KEY_SECRET,
});
const s3 = new AWS.S3({ endpoint:  process.env.CELLAR_ADDON_HOST });


// Define your S3 bucket name and object key
const bucketName = 'polysnap';
const objectKey = 'verybeautiful.jpg';


// Creating a server instance
const server = createServer(async (req, res) => {
    // Add CORS headers to allow requests from any origin
    res.setHeader('Access-Control-Allow-Origin', '*'); // This allows requests from any origin
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS'); // Allow the methods you intend to support
    res.setHeader('Access-Control-Allow-Headers', 'Content-Type'); // Allow custom headers if needed

    if (req.method === 'POST' && req.url === '/upload') {
        try {
            console.log(req.body);
            console.log(req.image);
            if (!req.image) {
                return res.status(400).send('No image data found.');
            }
            const imageBuffer = req.body.image;
            const mimeType = mime.getType(imageBuffer);
            const fileExtension = mime.getExtension(mimeType);
            const fileName = `image_${Date.now()}.${fileExtension}`;
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
    }
    else{
        // Upload a file to S3 when a request is received
        try {
            const imageFile = './beautiful.jpg';
            const imageBuffer = fs.readFileSync(imageFile);

            const fileContent = 'Hello, World!';
            const params = {
                Bucket: bucketName,
                Key: objectKey,
                Body: imageBuffer,
                ACL: 'public-read',
            };
            console.log(`S3 Endpoint: ${process.env.CELLAR_ADDON_HOST}`);
            console.log('S3 Upload Params:', params);
            s3.upload(params, function (err, data) {
                if (err) {
                    console.error("Error uploading image:", err);
                    res.status(500).send('Error uploading image');
                    res.end('Error uploading image');
                } else {
                    console.log("Image uploaded successfully:", data.Location);
                    res.writeHead(200, { 'Content-Type': 'text/plain' });
                    res.end('Image uploaded successfully');
                }
            });
            console.log(`File uploaded successfully to ${bucketName}/${objectKey}`);
        } catch (error) {
            console.error(`Error uploading file: ${error.message}`);
            res.writeHead(500, { 'Content-Type': 'text/plain' });
            res.end('Error uploading image');
        }
    }

});

// Define the hostname and port on which the server will listen
const hostname = '0.0.0.0'; // You can change this to your preferred hostname
const port = 8080; // You can change this to your preferred port number

// Start the server
server.listen(port, hostname, () => {
    console.log(`Server is running on port ${port}`);

});
