package br.edu.iftm.tspi.pbackorm.e_commerce.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.iftm.tspi.pbackorm.e_commerce.domain.Categoria;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.CategoriaDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ValorTotalPorCategoriaDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.ProdutoDTO;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.CategoriaMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.dto.mapper.ProdutoMapper;
import br.edu.iftm.tspi.pbackorm.e_commerce.repository.CategoriaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;



@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {
     
    private final CategoriaRepository repository;
    private final CategoriaMapper mapper;
    private final ProdutoMapper mapperProdutos;
    @GetMapping
    public List<CategoriaDTO> listar(@RequestParam(required = false) String nome){
        nome = (nome == null) ? "": nome;
        List<Categoria> categorias = repository.findByNomeContainingIgnoreCase(nome);
        List<CategoriaDTO> categoriasDTO = mapper.toDtoList(categorias);
        return categoriasDTO;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDTO> buscarPorId(@PathVariable Integer id){
        Categoria categoria = repository.findById(id)
                             .orElseThrow(() -> new EntityNotFoundException
                             ("Categoria de id " +id+ " não encontrado "));
        CategoriaDTO  categoriaDTO = mapper.toDto(categoria);
        return ResponseEntity.ok().body(categoriaDTO);
    }
    
    @GetMapping("/{id}/produtos")
    public ResponseEntity <List<ProdutoDTO>> buscarProdutos(@PathVariable Integer id) {
        Categoria categoria = repository.findById(id) 
                              .orElseThrow(()-> new EntityNotFoundException
                            ("Categoria de ID "+id+" não encontrado"));
        
        return ResponseEntity.ok().body(mapperProdutos.toDtoList(categoria.getProdutos()));
    }
    
    @PostMapping
    public ResponseEntity<CategoriaDTO> novo(@Valid @RequestBody CategoriaDTO novo) {
          Categoria salvoEntity = mapper.toEntity(novo);
          Categoria categoriaSalva = repository.save(salvoEntity);
          CategoriaDTO categoriaDTO = mapper.toDto(categoriaSalva);

        return ResponseEntity
                 .status(HttpStatus.CREATED)
                 .body(categoriaDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDTO> atualizar(@PathVariable Integer id,
        @Valid @RequestBody CategoriaDTO categoriaDTO) {
       if(!repository.existsById(id)){
           throw new EntityNotFoundException("Categoria de ID "+id+" não encontrado");
       }
       Categoria categoriaAtualizar = mapper.toEntity(categoriaDTO);
       categoriaAtualizar.setId(id);
        CategoriaDTO categoriaAtualizarDTO = mapper.toDto(categoriaAtualizar);
        return ResponseEntity.ok(categoriaAtualizarDTO);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id){
        Categoria categoria = repository.findById(id)
                              .orElseThrow(()-> new EntityNotFoundException
                              ("Categoria de ID "+id+" não encontrada"));
        repository.delete(categoria);
        return ResponseEntity.noContent().build();                      
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<List<ValorTotalPorCategoriaDTO>> buscaTotalPorCategoria(@PathVariable Integer id) {
        List<ValorTotalPorCategoriaDTO> valorTotalPorCategoria = repository.buscaTotalPorCategoria(id);
        if (valorTotalPorCategoria.isEmpty()) {
            return ResponseEntity.ok().body(List.of());
        }
        return ResponseEntity.ok().body(valorTotalPorCategoria);
    }
}
