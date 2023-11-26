package com.storyscawler.service.tag;

import com.storyscawler.infrastructure.model.entity.JpaTag;
import com.storyscawler.infrastructure.repository.JpaTagRepository;
import com.storyscawler.infrastructure.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final JpaTagRepository jpaTagRepository;

    @Override
    @Transactional
    public JpaTag findByTitleOrElseCreate(String title) {
        var tagOpt = jpaTagRepository.findFirstByTitle(title);
        return tagOpt.orElseGet(() -> tryToCreateTag(title));
    }

    private JpaTag tryToCreateTag(String title) {
        var codeFromTitle = StringUtils.toKebabCase(title);
        var tagCode = codeFromTitle;
        var index = new AtomicInteger(0);
        while (jpaTagRepository.existsByCode(tagCode)) {
            tagCode = codeFromTitle + "-" + index.incrementAndGet();
        }
        var newTag = JpaTag.builder()
                .title(title)
                .code(tagCode)
                .build();
        return jpaTagRepository.save(newTag);
    }

}
