package uz.najottalim.javan6.dto.profiledto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {
    private Long id;
    private String username;
    @JsonProperty(defaultValue = "")
    private String bio;
    @JsonProperty(value = "image",defaultValue = "https://media.istockphoto.com/id/1173300976/vector/smile-face-icon-isolated-on-white-background-vector-illustration.jpg?s=2048x2048&w=is&k=20&c=JtVnm1aTKaAkeoaeHtaqaUU3uEzUUtKwebZfngY1rSc=")
    private String imagePath;
    @JsonProperty(defaultValue = "false")
    private Boolean following;
}
