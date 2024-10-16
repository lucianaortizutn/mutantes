package com.example.mutantes.services;

import com.example.mutantes.entities.Base;

import java.io.Serializable;
import java.util.List;

public interface BaseService<E extends Base, ID extends Serializable> {
    public List<E> findAll() throws Exception;
    public E save(E entity) throws Exception;
}
