package fr.teama.messagepublisherservice.exceptions;

public class UserAlreadyExistException extends Exception {
    public UserAlreadyExistException() {
        super("User already exist");
    }
}