// package com.example.bookstore.Configuration;

// import java.math.BigDecimal;
// import java.util.Arrays;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import com.example.bookstore.Entities.Books;
// import com.example.bookstore.Repository.BooksRepository;

// @Component
// public class DataInitializer implements CommandLineRunner {

//     @Autowired
//     private BooksRepository booksRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         // Check if books already exist to avoid duplicate initialization
//         if (booksRepository.count() > 0) {
//             return;
//         }

//         // Sample books data
//         List<Books> books = Arrays.asList(
//             createBook("The Great Gatsby", "F. Scott Fitzgerald", 
//                 "A novel about the decadence and excess of the Jazz Age", 
//                 new BigDecimal("12.99"), 
//                 "https://covers.openlibrary.org/b/id/8905409-L.jpg"),
                
//             createBook("To Kill a Mockingbird", "Harper Lee", 
//                 "A novel about racial inequality through the eyes of a young girl", 
//                 new BigDecimal("14.99"), 
//                 "https://covers.openlibrary.org/b/id/8315302-L.jpg"),
                
//             createBook("1984", "George Orwell", 
//                 "A dystopian novel set in a totalitarian state", 
//                 new BigDecimal("11.99"), 
//                 "https://covers.openlibrary.org/b/id/7222246-L.jpg"),
                
//             createBook("The Catcher in the Rye", "J.D. Salinger", 
//                 "A novel about teenage angst and alienation", 
//                 new BigDecimal("13.50"), 
//                 "https://covers.openlibrary.org/b/id/6926521-L.jpg"),
                
//             createBook("Pride and Prejudice", "Jane Austen", 
//                 "A romantic novel about the societal expectations of women", 
//                 new BigDecimal("9.99"), 
//                 "https://covers.openlibrary.org/b/id/6979861-L.jpg"),
                
//             createBook("The Hobbit", "J.R.R. Tolkien", 
//                 "A fantasy novel about a hobbit who goes on an adventure", 
//                 new BigDecimal("16.99"), 
//                 "https://covers.openlibrary.org/b/id/6979861-L.jpg"),
                
//             createBook("The Lord of the Rings", "J.R.R. Tolkien", 
//                 "An epic fantasy novel about a quest to destroy a powerful ring", 
//                 new BigDecimal("24.99"), 
//                 "https://covers.openlibrary.org/b/id/8323742-L.jpg"),
                
//             createBook("Harry Potter and the Philosopher's Stone", "J.K. Rowling", 
//                 "A fantasy novel about a young wizard's adventures at a wizarding school", 
//                 new BigDecimal("19.99"), 
//                 "https://covers.openlibrary.org/b/id/8267857-L.jpg"),
                
//             createBook("The Alchemist", "Paulo Coelho", 
//                 "A novel about following your dreams and finding your destiny", 
//                 new BigDecimal("10.99"), 
//                 "https://covers.openlibrary.org/b/id/8304410-L.jpg"),
                
//             createBook("The Da Vinci Code", "Dan Brown", 
//                 "A mystery thriller novel about hidden religious symbols", 
//                 new BigDecimal("15.99"), 
//                 "https://covers.openlibrary.org/b/id/6918021-L.jpg"),
                
//             createBook("The Hunger Games", "Suzanne Collins", 
//                 "A dystopian novel about a televised fight to the death", 
//                 new BigDecimal("13.99"), 
//                 "https://covers.openlibrary.org/b/id/8059161-L.jpg"),
                
//             createBook("The Diary of a Young Girl", "Anne Frank", 
//                 "The diary of a Jewish girl hiding during the Holocaust", 
//                 new BigDecimal("8.99"), 
//                 "https://covers.openlibrary.org/b/id/8399253-L.jpg"),
                
//             createBook("Brave New World", "Aldous Huxley", 
//                 "A dystopian novel about a genetically engineered future society", 
//                 new BigDecimal("12.50"), 
//                 "https://covers.openlibrary.org/b/id/8086410-L.jpg"),
                
//             createBook("The Shining", "Stephen King", 
//                 "A horror novel about a family trapped in a haunted hotel", 
//                 new BigDecimal("14.50"), 
//                 "https://covers.openlibrary.org/b/id/8881767-L.jpg"),
                
//             createBook("The Chronicles of Narnia", "C.S. Lewis", 
//                 "A fantasy series about a magical world accessible through a wardrobe", 
//                 new BigDecimal("21.99"), 
//                 "https://covers.openlibrary.org/b/id/8302537-L.jpg")
//         );
        
//         // Save all books to the database
//         booksRepository.saveAll(books);
        
//         System.out.println("Database initialized with " + books.size() + " sample books");
//     }
    
//     private Books createBook(String title, String author, String description, BigDecimal price, String imageUrl) {
//         Books book = new Books();
//         book.setTitle(title);
//         book.setAuthor(author);
//         book.setDescription(description);
//         book.setPrice(price);
//         book.setImage(imageUrl);
//         book.setApproved(true); // All sample books are pre-approved
//         return book;
//     }
// } 