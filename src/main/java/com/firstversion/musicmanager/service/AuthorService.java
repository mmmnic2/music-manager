package com.firstversion.musicmanager.service;

import com.firstversion.musicmanager.dto.request.AuthorRequest;
import com.firstversion.musicmanager.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse createAuthor(AuthorRequest authorRequest);
    boolean deleteAuthor(List<Long> authorIds);
    List<AuthorResponse> getAllAuthors();
}
