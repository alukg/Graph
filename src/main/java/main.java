import javafx.util.Pair;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.BellmanFordShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import java.util.*;

public class main {
    public static void main(String[] args) {
        Graph g = creatRandomGraph(100,10,10);
        print (g);
        Graph g1 = SPANNER(g, 2);
        print(g1);

    }

    public static Graph SPANNER(final Graph<String, DefaultWeightedEdge> g, int r) {
        Set<DefaultWeightedEdge> edgeSet = g.edgeSet();
        DefaultWeightedEdge[] edgeArray = edgeSet.toArray(new DefaultWeightedEdge[edgeSet.size()]);
        Arrays.sort(edgeArray, new Comparator<DefaultWeightedEdge>() {
            @Override
            public int compare(DefaultWeightedEdge o1, DefaultWeightedEdge o2) {
                return (int) (g.getEdgeWeight(o1) - g.getEdgeWeight(o2));
            }
        });
        SimpleWeightedGraph<String, DefaultWeightedEdge> g1 = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        vertexCopy(g, g1);


        for (int i = 0; i < edgeArray.length; i++) {
            BellmanFordShortestPath b = new BellmanFordShortestPath(g1);
            DefaultWeightedEdge e = edgeArray[i];
            String source = g.getEdgeSource(e);
            String target = g.getEdgeTarget(e);
            double shortestPathWeight = b.getPathWeight(source, target);
            if (g.getEdgeWeight(e) * r < shortestPathWeight)
                g1.addEdge(source, target, e);

        }
        return g1;


    }

    private static Graph<String, DefaultWeightedEdge> createWeightedGraph() {
        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);

        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String e = "e";

        // add the vertices
        g.addVertex(a);
        g.addVertex(b);
        g.addVertex(c);
        g.addVertex(d);
        g.addVertex(e);
        // add edges to create a circuit
        DefaultWeightedEdge edge;
        edge = g.addEdge(a, b);
        g.setEdgeWeight(edge, 4);

        edge = g.addEdge(a, e);
        g.setEdgeWeight(edge, 4);

        edge = g.addEdge(b, e);
        g.setEdgeWeight(edge, 4);

        edge = g.addEdge(b, c);
        g.setEdgeWeight(edge, 3);

        edge = g.addEdge(b, d);
        g.setEdgeWeight(edge, 5);

        edge = g.addEdge(e, c);
        g.setEdgeWeight(edge, 5);

        edge = g.addEdge(e, d);
        g.setEdgeWeight(edge, 3);

        edge = g.addEdge(c, d);
        g.setEdgeWeight(edge, 4);

        return g;
    }

    private static void vertexCopy(Graph g, Graph g1) {
        Iterator<String> iter = g.vertexSet().iterator();
        while (iter.hasNext()) {
            g1.addVertex(iter.next());
        }
    }

    private static void print(Graph g1) {
        Iterator<DefaultWeightedEdge> iter = g1.edgeSet().iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next().toString());
        }
    }

    private static Graph<String, DefaultWeightedEdge> creatRandomGraph(int vertex, int edges, int maxWeight) {
        SimpleWeightedGraph<String, DefaultWeightedEdge> g = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
        for (int i = 0; i < vertex; i++) {
            g.addVertex(Integer.toString(i));
        }
        Pair<String, String>[] vertexPairs=new Pair[fact(vertex)/(fact(2)*fact(vertex-2))];
        int curr=0;
        for (int i=0; i<vertex; i++){
            for (int j=i+1; j<vertex; j++){
                vertexPairs[curr]=new Pair<>(Integer.toString(i),Integer.toString(j));
                curr++;
            }
        }

        /**
         * shuffle array
         */
        Arrays.sort(vertexPairs, new Comparator<Pair<String, String>>() {
            @Override
            public int compare(Pair<String, String> o1, Pair<String, String> o2) {
                if (Math.random()>0.5)
                    return 1;
                else
                    return -1;
            }
        });

        for (int i=0; i<edges; i++){
            DefaultWeightedEdge edge=g.addEdge(vertexPairs[i].getKey(),vertexPairs[i].getValue());
            g.setEdgeWeight(edge,(int)Math.random()*maxWeight+1);
        }

        return g;
    }
    private static int fact(int n){
        int acc=1;
        for (int i=1; i<=n; i++)
            acc=acc*i;
        return acc;

    }
}