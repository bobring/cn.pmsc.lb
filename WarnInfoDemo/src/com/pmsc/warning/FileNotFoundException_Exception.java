
package com.pmsc.warning;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 3.0.1
 * 2017-02-14T09:51:29.988+08:00
 * Generated source version: 3.0.1
 */

@WebFault(name = "FileNotFoundException", targetNamespace = "http://service.warning.pmsc.com/")
public class FileNotFoundException_Exception extends Exception {
    
    private com.pmsc.warning.FileNotFoundException fileNotFoundException;

    public FileNotFoundException_Exception() {
        super();
    }
    
    public FileNotFoundException_Exception(String message) {
        super(message);
    }
    
    public FileNotFoundException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotFoundException_Exception(String message, com.pmsc.warning.FileNotFoundException fileNotFoundException) {
        super(message);
        this.fileNotFoundException = fileNotFoundException;
    }

    public FileNotFoundException_Exception(String message, com.pmsc.warning.FileNotFoundException fileNotFoundException, Throwable cause) {
        super(message, cause);
        this.fileNotFoundException = fileNotFoundException;
    }

    public com.pmsc.warning.FileNotFoundException getFaultInfo() {
        return this.fileNotFoundException;
    }
}
