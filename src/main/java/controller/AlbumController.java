package controller;

import model.Album;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/AlbumController")
public class AlbumController extends HttpServlet {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("MusicAppPU");

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntityManager em = emf.createEntityManager();

        try {
            List<Album> albums = em.createQuery("SELECT a FROM Album a", Album.class).getResultList();
            req.setAttribute("albums", albums);
            req.getRequestDispatcher("page/Home.jsp").forward(req, resp);
        } finally {
            em.close();
        }
    }

    @Override
    public void destroy() {
        emf.close();
    }
}
