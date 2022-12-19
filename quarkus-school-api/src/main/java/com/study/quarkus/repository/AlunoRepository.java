package com.study.quarkus.repository;

import com.study.quarkus.model.Aluno;
import com.study.quarkus.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer> {
    public List<Aluno> getTutoradosByProfessor(Professor professor) {

        Objects.requireNonNull(professor, "Professor must be not null");

        var query = find("tutor", Sort.ascending("name"), professor);

        return query.list();
    }
}
