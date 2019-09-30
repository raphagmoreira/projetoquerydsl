package br.com.querydsl.domain.service;

import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.exception.EntityNotFoundException;
import br.com.querydsl.domain.query.Query;
import br.com.querydsl.domain.query.Sorter;
import br.com.querydsl.domain.query.impl.PessoaFilter;
import br.com.querydsl.domain.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Created by Rapha on 08/08/2019.
 */
@Service
public class PessoaService implements Serializable {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> find(PessoaFilter pessoaFilter,
                             String sortProperty,
                             Sorter.Direction sortDirection,
                             Long page) {
        //Constrói a query para a entidade Pessoa
        final Query<Pessoa> query = Query.<Pessoa>builder()
                .filter(pessoaFilter)
                .sort(Sorter.<Pessoa>by(sortProperty).direction(sortDirection))
                .page(page)
                .build();

        return pessoaRepository.find(query);
    }

    public Pessoa findById(Long id) {
        return pessoaRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public void create(Pessoa pessoa) {
        pessoaRepository.create(pessoa);
    }

    @Transactional
    public void update(Pessoa pessoa) {
        pessoaRepository.update(pessoa);
    }

    @Transactional
    public void remove(Long idPessoa) {
        pessoaRepository.removeById(idPessoa);
    }
}
