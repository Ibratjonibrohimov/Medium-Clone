package uz.najottalim.javan6.dto.userdto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String email;
    private String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String bio;
    @JsonProperty(value = "image",defaultValue = "https://api.realworld.io/images/smiley-cyrus.jpeg")
    private String imagePath;
    private String token;
}
