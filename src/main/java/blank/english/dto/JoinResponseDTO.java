package blank.english.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class JoinResponseDTO {

    String nickName;
    String email;
    String tokenId;

    @Builder
    public JoinResponseDTO(String nickName, String email, String tokenId) {
        this.nickName = nickName;
        this.email = email;
        this.tokenId = tokenId;
    }
}
