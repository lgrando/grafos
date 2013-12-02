package br.com.senac.alg.grafos;

public class Dijkstra {
	private int antecessor[];
	private double pesoAresta[];
	private Grafo grafo;
	private StringBuilder caminho = new StringBuilder();

	public Dijkstra(Grafo grafo) {
		this.grafo = grafo;
	}

	public void obterArvoreCMC(int raiz) throws Exception {
		int n = this.grafo.numVertices();
		this.pesoAresta = new double[n];
		int vs[] = new int[n + 1];
		this.antecessor = new int[n];
		
		for (int u = 0; u < n; u++) {
			this.antecessor[u] = -1;
			pesoAresta[u] = Double.MAX_VALUE;
			vs[u + 1] = u;
		}
		
		pesoAresta[raiz] = 0;
		FPHeapMinIndireto heap = new FPHeapMinIndireto(pesoAresta, vs);
		heap.constroi();
		
		while (!heap.vazio()) {
			int u = heap.retiraMin();
			if (!this.grafo.listaAdjVazia(u)) {
				Grafo.Aresta adj = grafo.primeiroListaAdj(u);
				while (adj != null) {
					int v = adj.v2();
					if (this.pesoAresta[v] > (this.pesoAresta[u] + adj.peso())) {
						antecessor[v] = u;
						heap.diminuiChave(v, this.pesoAresta[u] + adj.peso());
					}
					adj = grafo.proxAdj(u);
				}
			}
		}
	}

	public int antecessor(int u) {
		return this.antecessor[u];
	}

	public double peso(int u) {
		return this.pesoAresta[u];
	}

	public StringBuilder imprimeCaminho(int origem, int vertice) throws Exception {
		if (origem == vertice)
			caminho.append(origem);
		else if (this.antecessor[vertice] == -1)
			throw new Exception("Nao existe caminho de " + origem + " ate " + vertice);
		else {
			caminho.append(vertice + " > ");
			imprimeCaminho(origem, this.antecessor[vertice]);
		}
		
		return caminho;
		
	}

}