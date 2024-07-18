package com.security.serviceImpl;

import java.util.List;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.security.dao.AuthenticationResponse;
import com.security.dao.LoginBook;
import com.security.dao.RegisterBook;
import com.security.filter.JwtuserDetailsService;
import com.security.model.BookUser;
import com.security.repository.BookRepository;
import com.security.service.BookServiceInter;

@Service
public class BookServiceImpl implements BookServiceInter {

    private final BookRepository bookRepo;
    private final JwtuserDetailsService jwtServ;
    private final PasswordEncoder pswd;
    private final AuthenticationManager authManager;
    private final CustomDeatilsService customServ;

    public BookServiceImpl(BookRepository bookRepo,
    		               JwtuserDetailsService jwtServ, 
    		                   PasswordEncoder pswd, 
    		              AuthenticationManager authManager,
    		                  CustomDeatilsService customServ) {
        this.bookRepo = bookRepo;
        this.jwtServ = jwtServ;
        this.pswd = pswd;
        this.authManager = authManager;
        this.customServ=customServ;
    }

    @Override
    public AuthenticationResponse register(RegisterBook book) {
        BookUser bookUser = new BookUser();
        bookUser.setFirstName(book.getFirstName());
        bookUser.setLastName(book.getLastName());
        bookUser.setAddress(book.getAddress());
        bookUser.setEmail(book.getEmail());
        bookUser.setPassword(pswd.encode(book.getPassword()));
        bookUser.setPhone(book.getPhone());
        bookUser.setRoles(book.getRoles());

        bookRepo.save(bookUser);  // Ensure the user is saved in the repository

        String generateToken = jwtServ.generateToken(bookUser);
        return new AuthenticationResponse(generateToken);
    }

    @Override
    public List<BookUser> getAllBooks() {
        return bookRepo.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        bookRepo.deleteById(id);
    }

    @Override
    public AuthenticationResponse authenticate(LoginBook book) {
        if (authManager == null) {
            throw new IllegalStateException("Authentication is not available.");
        }

        UserDetails userDetails = customServ.loadUserByUsername(book.getEmail());

        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        book.getEmail(),
                        book.getPassword(),
                        userDetails.getAuthorities())
        );

        BookUser bookUser = bookRepo.findByEmail(book.getEmail()).orElseThrow(()->new UsernameNotFoundException("user not found!"));
        String generateToken = jwtServ.generateToken(bookUser);
        return new AuthenticationResponse(generateToken);
    }
}
