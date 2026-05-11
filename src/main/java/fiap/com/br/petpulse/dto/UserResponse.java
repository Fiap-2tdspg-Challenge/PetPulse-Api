package fiap.com.br.petpulse.dto;

import fiap.com.br.petpulse.model.User;

public record UserResponse(
        Long id,
        String name,
        String cpf,
        String email,
        String password,
        String phone,
        String adress
) {
    public static UserResponse fromEntity(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getCpf(),
                user.getEmail(),
                user.getPassword(),
                user.getPhone(),
                user.getAdress()
        );
    }
}
