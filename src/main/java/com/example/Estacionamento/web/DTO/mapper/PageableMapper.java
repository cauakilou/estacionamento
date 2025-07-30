package com.example.Estacionamento.web.DTO.mapper;

import com.example.Estacionamento.web.DTO.PageableDTO;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

public class PageableMapper {
    public static PageableDTO pageableToDTO(Page page){
        return new ModelMapper().map(page, PageableDTO.class);
    }
    private PageableMapper(){}
}
