package com.example.Intesis.Entity.User;

import java.util.List;

public record LoginDTO(String token, List<String> role) {
}
