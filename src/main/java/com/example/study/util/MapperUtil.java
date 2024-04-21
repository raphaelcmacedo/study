package com.example.study.util;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MapperUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public <S, D> D map(S source, Class<D> destinationClass) {
        return modelMapper.map(source, destinationClass);
    }
    public <S, D>List<D> mapList(List<S> source, Class<D> destinationClass) {
        return source.stream()
                .map(element -> map(element, destinationClass))
                .collect(Collectors.toList());
    }
    public <S, D> Page<D> mapPage(Page<S> source, Class<D> destinationClass) {
        return source.map(element -> map(element, destinationClass));
    }

}
