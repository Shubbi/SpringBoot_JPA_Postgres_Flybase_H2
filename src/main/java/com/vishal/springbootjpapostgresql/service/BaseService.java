package com.vishal.springbootjpapostgresql.service;

import com.vishal.springbootjpapostgresql.dto.TutorialDto;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {
    public T create(T obj);
    public T read(Integer id);
    public T update(T obj);
}