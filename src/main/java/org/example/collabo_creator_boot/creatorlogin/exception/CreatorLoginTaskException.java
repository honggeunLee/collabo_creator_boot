package org.example.collabo_creator_boot.creatorlogin.exception;
import lombok.Data;

@Data
public class CreatorLoginTaskException extends RuntimeException {

    private int status;
    private String msg;

    public CreatorLoginTaskException(final int status, final String msg) {
        super(status +"_" + msg);
        this.status = status;
        this.msg = msg;
    }

}