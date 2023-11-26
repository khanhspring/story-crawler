package com.storyscawler.service.author;

import com.storyscawler.infrastructure.model.entity.JpaAuthor;
import com.storyscawler.infrastructure.repository.JpaAuthorRepository;
import com.storyscawler.infrastructure.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final JpaAuthorRepository jpaAuthorRepository;

    @Override
    @Transactional
    public JpaAuthor findByNameOrElseCreate(String name) {
        var authorOpt = jpaAuthorRepository.findFirstByFullName(name);
        return authorOpt.orElseGet(() -> tryToCreateAuthor(name));
    }

    private JpaAuthor tryToCreateAuthor(String name) {
        var codeFromName = StringUtils.toKebabCase(name);
        var authorCode = codeFromName;
        var index = new AtomicInteger(0);
        while (jpaAuthorRepository.existsByCode(authorCode)) {
            authorCode = codeFromName + "-" + index.incrementAndGet();
        }
        var newAuthor = JpaAuthor.builder()
                .fullName(name)
                .code(authorCode)
                .build();
        return jpaAuthorRepository.save(newAuthor);
    }
}
