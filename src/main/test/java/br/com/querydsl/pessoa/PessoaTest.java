package br.com.querydsl.pessoa;

import br.com.querydsl.Application;
import br.com.querydsl.domain.entity.Pessoa;
import br.com.querydsl.domain.query.impl.PessoaFilter;
import br.com.querydsl.domain.service.PessoaService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class PessoaTest {

    @Autowired
    private PessoaService pessoaService;

    @Test
    public void findPessoaById() {
        List<Pessoa> pessoaList = pessoaService.find(new PessoaFilter(1L, null), null, null, null);
        Assertions.assertThat(pessoaList)
                .isNotEmpty();
    }

    @Test
    public void findPessoaByName() {
        List<Pessoa> pessoaList = pessoaService.find(new PessoaFilter(null, "JOAO"), null, null, null);
        Assertions.assertThat(pessoaList)
                .isNotEmpty();
    }

    @Test
    public void findPessoaId() {
        Pessoa pessoa = pessoaService.findById(1L);
        Assertions.assertThat(pessoa)
                .isNotNull();
    }

    @Test
    public void findPessoasSubQuery() {
        List<Pessoa> pessoaList = pessoaService.findPessoasSubQuery();
        Assertions.assertThat(pessoaList)
                .isNotEmpty();
    }

    @Test
    public void findPessoaPageable() {
        Pageable pageable = new Pageable() {
            @Override
            public int getPageNumber() {
                return 0;
            }

            @Override
            public int getPageSize() {
                return 0;
            }

            @Override
            public long getOffset() {
                return 0;
            }

            @Override
            public Sort getSort() {
                return null;
            }

            @Override
            public Pageable next() {
                return null;
            }

            @Override
            public Pageable previousOrFirst() {
                return null;
            }

            @Override
            public Pageable first() {
                return null;
            }

            @Override
            public boolean hasPrevious() {
                return false;
            }
        };

        Page<Pessoa> pessoaList = pessoaService.findPage(null, pageable);
        Assertions.assertThat(pessoaList)
                .isNotEmpty();
    }

    @Test
    public void create() {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome("Teste");
        pessoaService.create(pessoa);

        List<Pessoa> pessoaList = pessoaService.find(new PessoaFilter(null, "Teste"), null, null, null);
        Assertions.assertThat(pessoaList)
                .isNotNull();
    }

    @Test
    public void update() {
        Pessoa pessoa = new Pessoa();

        pessoa.setNome("Teste");
        pessoaService.create(pessoa);

        List<Pessoa> pessoaList = pessoaService.find(new PessoaFilter(null, "Teste"), null, null, null);
        Assertions.assertThat(pessoaList)
                .isNotNull();

        pessoaList.get(0).setNome("TesteUpdate");
        pessoaService.update(pessoaList.get(0));

        List<Pessoa> pessoaFind = pessoaService.find(new PessoaFilter(null, "TesteUpdate"), null, null, null);

        Assertions.assertThat(pessoaList.get(0).getNome())
                .isEqualTo(pessoaFind.get(0).getNome());
    }

    @Test
    public void delete() {
        List<Pessoa> pessoaFind = pessoaService.find(new PessoaFilter(null, "Teste"), null, null, null);
        Assertions.assertThat(pessoaFind)
                .isNotNull();

        pessoaService.remove(pessoaFind.get(0).getId());

        Optional<List<Pessoa>> pessoaList = Optional.ofNullable(pessoaService.find(new PessoaFilter(null, "Teste"), null, null, null));
        Assertions.assertThat(pessoaList.isPresent())
                .isTrue();
    }

}
