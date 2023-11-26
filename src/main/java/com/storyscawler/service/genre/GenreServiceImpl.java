package com.storyscawler.service.genre;

import com.storyscawler.infrastructure.model.entity.JpaGenre;
import com.storyscawler.infrastructure.repository.JpaGenreRepository;
import com.storyscawler.infrastructure.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final JpaGenreRepository jpaGenreRepository;

    @Override
    @Transactional
    public JpaGenre findByTitleOrElseCreate(String title) {
        var genreOpt = jpaGenreRepository.findFirstByTitle(title);
        return genreOpt.orElseGet(() -> tryToCreateGenre(title));
    }

    private JpaGenre tryToCreateGenre(String title) {
        var codeFromTitle = StringUtils.toKebabCase(title);
        var genreCode = codeFromTitle;
        var index = new AtomicInteger(0);
        while (jpaGenreRepository.existsByCode(genreCode)) {
            genreCode = codeFromTitle + "-" + index.incrementAndGet();
        }
        var newGenre = JpaGenre.builder()
                .title(title)
                .code(genreCode)
                .build();
        return jpaGenreRepository.save(newGenre);
    }

}
