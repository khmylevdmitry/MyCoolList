package org.islandsimulation;

import org.islandsimulation.simulation.EvolutionSimulation;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        EvolutionSimulation evolutionSimulation = new EvolutionSimulation();
        evolutionSimulation.startSimulation();
    }
}

