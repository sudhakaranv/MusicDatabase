# MusicDatabase

Technologies Used

Java 8, Spring Boot 2, Maven 4
For removing bolier plate code --- Lombok
API Documentation - Swagger2
Json parsing / handling -----GSON, Jackson
Testing - Junit jupiter, Mockito, MockMvc
Embedded DB - H2

Swagger UI -- http://localhost:9090/swagger-ui.html


COMPLETED TASKS

     WITHIN THREE HOUR BOUNDARY
     
     ARTIST endpoints
     
     1) get -> /artists -------------------------------EASY(Proper repository method with specification needed for sorting, paging and                                                              filtering)
     
     2) post -> /artists-------------------------------EASY
     
     3) put -> /artists/{artistId}---------------------EASY
     
    
    ALBUM endpoints  (Required proper handling of ManyToOne(album-artist) and manyToMany (album-genre)                                                         relationships)
    
           ONE DOUBT HERE--Accoring to assesment its one to many releationship between artist and album but in real scenario
           multiple artists can work on single album
     
     4) get -> /artists/{artistId}/albums -------- EASY but require proper attention
      
     5) post -> /artists/{artistId}/albums------------.EASY but require proper attention
     
     6) put -> /artists/{artistId}/albums/{albumId}---- EASY but require proper attention
    
     7) EXTRA---Dockerization - EASY
     
     8) Swagger Documentation - EASY 
    
     
      
      OUTSIDE THREE HOUR
      
      6) get -> /artists/{artistId}/albums/albumId ---(Skipped filtering part)
      
           Got stuck here because of DISCOGS--- searching with title (artist name - release title)  not working properly.
           
           I found an work around - I found the album URI has artist name+album name where spaces are replaced by hiphen,
           as shown below.
           
           https://www.discogs.com/Michael-Jackson-Got-To-Be-There/master/121917
           
           so I searched with Query and album name combo(alubm name is additional proaly not needed)
           
           Query  = (artistName+alubmName) replacing all spaces with hiphen...
           
           This gave correct results
           
      7) Test cases 
        
      
    NOT COMPLETED
    
     1) Genre/s based filtering in get -> /artists/{artistId}/albums/albumId
     
              ----- tricky as the filtering is based on relationship table. I got stuck with that, I would love to do it, but skipping                       now for timesake 
              
     2) API Metrics (can be done with actuator/micrometer. need more time though)
     
     3) Reactive ---I started with servlet approach(Spring MVC) , reactive approach(spring webflux) would be a rework so skipped.
     
     

     
     
