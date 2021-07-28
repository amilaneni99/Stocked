package com.abhinav.stockexchangeapplication.util;

import com.abhinav.stockexchangeapplication.model.Company;
import com.abhinav.stockexchangeapplication.model.IPODetails;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    public IPODetails copyObject(IPODetails source, IPODetails objectToBeCopied) {
        source.setOpenDateTime(objectToBeCopied.getOpenDateTime());
        source.setPricePerShare(objectToBeCopied.getPricePerShare());
        source.setTotalNumberOfShares(objectToBeCopied.getTotalNumberOfShares());

        return source;
    }

    public Company copyObject(Company source, Company objectToBeCopied) {
        source.setCompanyName(objectToBeCopied.getCompanyName());
        source.setSector(objectToBeCopied.getSector());
        source.setCeo(objectToBeCopied.getCeo());
        source.setCompanyBrief(objectToBeCopied.getCompanyBrief());
        source.setCeo(objectToBeCopied.getCeo());
        source.setTurnover(objectToBeCopied.getTurnover());
        source.setBoardOfDirectors(objectToBeCopied.getBoardOfDirectors());
        source.setSector(objectToBeCopied.getSector());
        return source;
    }
}
