package br.com.querydsl.application.resource;

import br.com.querydsl.configuration.annotation.QueryParam;
import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.query.Sorter;
import br.com.querydsl.domain.query.impl.PessoaFilter;
import br.com.querydsl.domain.service.PessoaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaResource {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de pessoas",
            notes = "O objeto retornado nao contém informações de paginação.",
            response = Pessoa[].class)
    public ResponseEntity<List<Pessoa>> find(@QueryParam PessoaFilter pessoaFilter,
                                             @RequestParam(value = "sortBy", required = false) String sortProperty,
                                             @RequestParam(value = "sortDirection", required = false) Sorter.Direction sortDirection,
                                             @RequestParam(value = "page", required = false) Long page) {
        return ResponseEntity.ok(
                pessoaService.find(pessoaFilter, sortProperty, sortDirection, page)
        );
    }

    @GetMapping(value = "/page", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Retorna uma lista de pessoas paginada.",
            notes = "O objeto retornado contém informações de paginação.",
            response = Pessoa[].class)
    public ResponseEntity<Page<Pessoa>> findPageble(@QueryParam PessoaFilter pessoaFilter,
                                                    Pageable pageable) {

        Page<Pessoa> pessoas = pessoaService.findPage(pessoaFilter, pageable);
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping(value = "/subselect", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pessoa>> findPageble() {

        List<Pessoa> pessoas = pessoaService.findPessoasSubQuery();
        return ResponseEntity.ok(pessoas);
    }

    @GetMapping(value = "/{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pessoa> findById(@PathVariable("idPessoa") final Long idPessoa) {
        return ResponseEntity.ok(pessoaService.findById(idPessoa));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> create(@RequestBody Pessoa pessoa) {
        pessoaService.create(pessoa);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").buildAndExpand(pessoa.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@RequestBody Pessoa pessoa) {
        pessoaService.update(pessoa);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{idPessoa}")
    public ResponseEntity<Void> remove(@PathVariable("idPessoa") Long idPessoa) {
        pessoaService.remove(idPessoa);
        return ResponseEntity.noContent().build();
    }

}
