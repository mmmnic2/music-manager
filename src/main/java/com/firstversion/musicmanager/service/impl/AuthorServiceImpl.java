package com.firstversion.musicmanager.service.impl;

import com.firstversion.musicmanager.dto.request.AuthorRequest;
import com.firstversion.musicmanager.dto.response.AuthorResponse;
import com.firstversion.musicmanager.exception.NotFoundException;
import com.firstversion.musicmanager.model.entity.Author;
import com.firstversion.musicmanager.repository.AuthorRepository;
import com.firstversion.musicmanager.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public AuthorResponse createAuthor(AuthorRequest authorRequest) {
        if (authorRequest.getAuthorName() == null || authorRequest.getAuthorName().trim().isEmpty()) {
            throw new RuntimeException("Author's name is not valid.");
        }
        Author author = new Author();
        author.setAuthorName(authorRequest.getAuthorName());
        Author savedAuthor = authorRepository.save(author);
        return savedAuthor.toAuthorResponse();
    }

    @Override
    public boolean deleteAuthor(List<Long> authorIds) {
        List<Author> foundAuthorList = authorRepository.findAllById(authorIds);
        if (foundAuthorList.isEmpty()) {
            throw new NotFoundException("Author not found.");
        }
        for (Author author : foundAuthorList) {
            authorRepository.delete(author);
        }
        return true;
    }

    @Override
    public List<AuthorResponse> getAllAuthors() {
        List<Author> authorList = authorRepository.findAll();
        if (authorList.isEmpty()) return List.of();
        return authorList.stream().map(Author::toAuthorResponse).toList();
    }
}
