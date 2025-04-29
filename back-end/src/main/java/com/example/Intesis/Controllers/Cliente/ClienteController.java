package com.example.Intesis.Controllers.Cliente;

import com.example.Intesis.Entity.Cliente.Cliente;
import com.example.Intesis.Respositories.ClienteRepository;
import com.example.Intesis.validation.VerifyCpfOrCnpj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private VerifyCpfOrCnpj validar;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    private List<Cliente> cliente(){
        return clienteRepository.findAll();
    }

    @GetMapping("/search")
    private List<Cliente> clienteName(@RequestParam String name){ return clienteRepository.findByNomeContainingIgnoreCase(name); }

    @GetMapping("/{id}")
    private Optional<Cliente> clienteId(@PathVariable UUID id){
        return clienteRepository.findById(id);
    }

    @PostMapping
    private ResponseEntity<String> cliente(@RequestBody Cliente body){
        if(validar.Validar(body.getDocumento())){
            clienteRepository.save(body);
            return ResponseEntity.ok("Cliente " + body.getNome() + " adicionado com sucesso");
        } else{
            return ResponseEntity.status(400).body("Documento: " + body.getDocumento() + " Documento Invalido.");
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<String> cliente(@PathVariable UUID id){
        try{
            clienteRepository.deleteById(id);
            return ResponseEntity.ok("Cliente Excluido");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Não Foi Possivel Excluir o Cliente");
        }
    }

    @PutMapping
    private ResponseEntity<String> clienteAdd(@RequestBody Cliente body){
        Cliente clienteExistente = clienteRepository.findById(body.getIdcliente()).orElse(null);
        if(clienteExistente != null){
            Cliente newCliente = new Cliente();
            if(validar.Validar(body.getDocumento())){
                newCliente.setIdcliente(body.getIdcliente());
                newCliente.setNome(body.getNome());
                newCliente.setDocumento(body.getDocumento());
            } else{
                ResponseEntity.status(404).body("Documento Invalido");
            }
            clienteRepository.save(newCliente);
            return ResponseEntity.ok("Cliente Atualizado Com Sucesso");
        } else{
            return ResponseEntity.status(404).body("Cliente Não Foi Atualizado");
        }
    }
}
