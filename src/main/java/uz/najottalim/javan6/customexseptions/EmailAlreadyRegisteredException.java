package uz.najottalim.javan6.customexseptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailAlreadyRegisteredException extends RuntimeException {
    private String message;
}
