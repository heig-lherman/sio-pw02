package sio.groupK.gui;

import javax.swing.*;
import sio.groupK.gui.model.DataSource;
import sio.groupK.gui.model.ConstructiveHeuristic;
import sio.groupK.gui.model.ImprovementHeuristic;
import sio.tsp.TspConstructiveHeuristic;
import sio.tsp.TspData;
import sio.tsp.TspImprovementHeuristic;

/**
 * Main application and frame for the TSP visualiser.
 */
public class TspApplication extends JFrame {

    private JPanel contentPane;
    private JComboBox<DataSource> data;
    private JComboBox<ConstructiveHeuristic> constructiveHeuristic;
    private JComboBox<ImprovementHeuristic> improvmentHeuristic;
    private JSlider startIndex;
    private JLabel startIndexLabel;
    private JLabel tourLengthLabel;
    private TourPanel tourPanel;

    public TspApplication() {
        super("TSP Visualiser");
        setContentPane(contentPane);

        // register field models and event listeners to update the tour
        data.setModel(new DefaultComboBoxModel<>(DataSource.values()));
        constructiveHeuristic.setModel(new DefaultComboBoxModel<>(ConstructiveHeuristic.values()));
        improvmentHeuristic.setModel(new DefaultComboBoxModel<>(ImprovementHeuristic.values()));

        data.addActionListener(e -> computeTour());
        constructiveHeuristic.addActionListener(e -> computeTour());
        improvmentHeuristic.addActionListener(e -> computeTour());
        startIndex.addChangeListener(e -> {
            startIndexLabel.setText(String.valueOf(startIndex.getValue()));
            computeTour();
        });

        // run a first computation
        computeTour();
    }

    /**
     * Compute the tour and update the panel.
     */
    private void computeTour() {
        try {
            var tourData = TspData.fromFile(((DataSource) data.getSelectedItem()).path());
            startIndex.setMaximum(tourData.getNumberOfCities() - 1);

            TspConstructiveHeuristic cHeuristic = ((ConstructiveHeuristic) constructiveHeuristic.getSelectedItem()).getHeuristicInstance();
            TspImprovementHeuristic iHeuristic = ((ImprovementHeuristic) improvmentHeuristic.getSelectedItem()).getHeuristicInstance();

            var tour = iHeuristic.computeTour(
                    cHeuristic.computeTour(tourData, startIndex.getValue())
            );
            tourPanel.setTour(tour);
            tourLengthLabel.setText("Tour length: %d".formatted(tour.length()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    e.getMessage(),
                    "An error occurred while loading data",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void createUIComponents() {
        tourPanel = new TourPanel();
    }

    /**
     * Main entry point for the visualiser.
     */
    public static void main(String[] args) {
        TspApplication app = new TspApplication();
        app.pack();
        app.setSize(1200, 600);
        app.setVisible(true);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
