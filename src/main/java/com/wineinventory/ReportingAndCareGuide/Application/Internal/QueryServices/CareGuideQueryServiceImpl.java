package com.wineinventory.ReportingAndCareGuide.Application.Internal.QueryServices;

import com.wineinventory.ReportingAndCareGuide.Domain.Model.Entities.CareGuide;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetAllCareGuidesByAccountIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetCareGuideByIdQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Model.Queries.GetCareGuideByTypeAndDescriptionQuery;
import com.wineinventory.ReportingAndCareGuide.Domain.Services.CareGuideQueryService;
import com.wineinventory.ReportingAndCareGuide.Infrastructure.Persistence.JPA.Repositories.CareGuideRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CareGuideQueryServiceImpl implements CareGuideQueryService {

    private final CareGuideRepository careGuideRepository;

    public CareGuideQueryServiceImpl(CareGuideRepository careGuideRepository) {
        this.careGuideRepository = careGuideRepository;
    }

    @Override
    public List<CareGuide> handle(GetCareGuideByIdQuery query) {
        return careGuideRepository.findById(query.id())
                .map(List::of)
                .orElseGet(List::of);
    }

    @Override
    public List<CareGuide> handle(GetCareGuideByTypeAndDescriptionQuery query) {
        return careGuideRepository.findByTypeAndDescription(
                query.type(),
                query.description()
        );
    }
    @Override
    public List<CareGuide> handle(GetAllCareGuidesByAccountIdQuery query) {
        return careGuideRepository.findByAccountId(query.accountId());
    }
}