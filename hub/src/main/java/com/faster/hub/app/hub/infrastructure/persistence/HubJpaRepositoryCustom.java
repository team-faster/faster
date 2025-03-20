package com.faster.hub.app.hub.infrastructure.persistence;

import com.faster.hub.app.hub.application.usecase.dto.request.SearchHubCondition;
import com.faster.hub.app.hub.domain.projection.SearchHubProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface HubJpaRepositoryCustom {
  Page<SearchHubProjection> searchHubsByCondition(Pageable pageable, SearchHubCondition condition);
}
