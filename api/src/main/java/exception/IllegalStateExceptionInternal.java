package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by JHXSMatthew on 27/9/18.
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class IllegalStateExceptionInternal extends RuntimeException {
}
