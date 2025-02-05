package com.anushka.bankcore.exceptions;


public class ResourceNotFoundException extends RuntimeException {
   public ResourceNotFoundException(String message) {
       super(message);
   }
}