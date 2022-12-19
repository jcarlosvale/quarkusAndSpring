package com.study.quarkus.repository;

import com.study.quarkus.model.Disciplina;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DisciplinaRepository  implements PanacheRepositoryBase<Disciplina, Integer> {
}
