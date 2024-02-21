package ru.gb.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.model.Issue;
import ru.gb.model.Reader;
import ru.gb.repository.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    private final ReaderRepository readerRepository;

    public List<Reader> getAllReaders() {
        return readerRepository.findAll();
    }

    public Reader getReaderById(Long id) {
        return readerRepository.findById(id).orElse(null);
    }

    public void addReader(Reader reader) {
        readerRepository.save(reader);
    }

    public void removeReaderById(Long id) {
        readerRepository.deleteById(id);
    }
    public List<Issue> getIssueByReaderId(Long id) {
        return readerRepository.findById(id).map(Reader::getListIssues).orElse(null);
    }
}

