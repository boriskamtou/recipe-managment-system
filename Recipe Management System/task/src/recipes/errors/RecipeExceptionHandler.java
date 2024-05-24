package recipes.errors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RecipeExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(RecipeNotFound.class)
    public ResponseEntity<?> handleRecipeNotFound(RecipeNotFound ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(UnauthorizedAction.class)
    public ResponseEntity<?> handleUnauthorizedAction(UnauthorizedAction ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        return ResponseEntity.badRequest().build();
    }
}
