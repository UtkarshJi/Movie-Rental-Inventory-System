//make sure you use import these libraries properly
//the code was tested in vscode


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;

class Movie {
    private String name;
    private boolean isRented;
    private ArrayList<Double> ratings;

    public Movie(String name) {
        this.name = name;
        this.isRented = false;
        this.ratings = new ArrayList<>();
    }

    public void rent() {
        if (!isRented) {
            isRented = true;
            System.out.println(name + " has been rented.");
        } else {
            System.out.println(name + " is already rented.");
        }
    }

    public void returnMovie() {
        if (isRented) {
            isRented = false;
            System.out.println(name + " has been returned.");
        } else {
            System.out.println(name + " is not rented.");
        }
    }

    public void addRating(double rating) {
        ratings.add(rating);
        System.out.println(name + " received a rating of " + rating + ".");
    }

    public double getAverageRating() {
        if (ratings.isEmpty()) {
            return 0;
        }
        double total = 0;
        for (double rating : ratings) {
            total += rating;
        }
        return total / ratings.size();
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Rented: " + isRented + ", Average Rating: " + String.format("%.2f", getAverageRating());
    }

    public String getName() {
        return name;
    }

    public boolean isRented() {
        return isRented;
    }
}

class MovieStore {
    private ArrayList<Movie> movies;

    public MovieStore() {
        this.movies = new ArrayList<>();
    }

    public void addMovie(String name) {
        Movie movie = new Movie(name);
        movies.add(movie);
        System.out.println(name + " has been added to the store.");
    }

    public void rentMovie(String name) {
        Movie movie = findMovie(name);
        if (movie != null) {
            movie.rent();
        } else {
            System.out.println(name + " is not in the store.");
        }
    }

    public void returnMovie(String name) {
        Movie movie = findMovie(name);
        if (movie != null) {
            movie.returnMovie();
        } else {
            System.out.println(name + " is not in the store.");
        }
    }

    public void rateMovie(String name, double rating) {
        Movie movie = findMovie(name);
        if (movie != null) {
            movie.addRating(rating);
        } else {
            System.out.println(name + " is not in the store.");
        }
    }

    public void showMovies() {
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    private Movie findMovie(String name) {
        for (Movie movie : movies) {
            if (movie.getName().equals(name)) {
                return movie;
            }
        }
        return null;
    }
}

public class MovieStoreGUI extends JFrame {
    private MovieStore store;
    private JTextArea outputArea;
    private JTextField nameField;
    private JTextField ratingField;

    public MovieStoreGUI() {
        store = new MovieStore();
        initializeStore(store);

        setTitle("Movie Store");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel nameLabel = new JLabel("Movie Name:");
        nameField = new JTextField();
        JLabel ratingLabel = new JLabel("Rating (0.0-5.0):");
        ratingField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ratingLabel);
        inputPanel.add(ratingField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2, 10, 10));

        JButton addButton = new JButton("Add Movie");
        addButton.addActionListener(e -> addMovie());

        JButton rentButton = new JButton("Rent Movie");
        rentButton.addActionListener(e -> rentMovie());

        JButton returnButton = new JButton("Return Movie");
        returnButton.addActionListener(e -> returnMovie());

        JButton rateButton = new JButton("Rate Movie");
        rateButton.addActionListener(e -> rateMovie());

        buttonPanel.add(addButton);
        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);
        buttonPanel.add(rateButton);

        JButton showButton = new JButton("Show Movies");
        showButton.addActionListener(e -> showMovies());

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14)); // Increased font size
        JScrollPane scrollPane = new JScrollPane(outputArea);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(showButton, BorderLayout.SOUTH);
        add(panel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void initializeStore(MovieStore store){}

    private void addMovie() {
        String name = nameField.getText();
        store.addMovie(name);
        nameField.setText("");
        showMovies();
    }

    private void rentMovie() {
        String name = nameField.getText();
        store.rentMovie(name);
        nameField.setText("");
        showMovies();
    }

    private void returnMovie() {
        String name = nameField.getText();
        store.returnMovie(name);
        nameField.setText("");
        showMovies();
    }

    private void rateMovie() {
        String name = nameField.getText();
        double rating;
        try {
            rating = Double.parseDouble(ratingField.getText());
            if (rating < 0.0 || rating > 5.0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException ex) {
            outputArea.setText("Invalid rating. Please enter a number between 0.0 and 5.0.");
            return;
        }
        store.rateMovie(name, rating);
        nameField.setText("");
        ratingField.setText("");
        showMovies();
    }

    private void showMovies() {
        outputArea.setText("");
        for (Movie movie : store.getMovies()) {
            outputArea.append(movie.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MovieStoreGUI gui = new MovieStoreGUI();
            gui.setVisible(true);
        });
    }
}
