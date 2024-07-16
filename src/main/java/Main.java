import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        List<Integer> list = new ArrayList<>();
        for(int i=0; i<N; ++i) {
            int a = Integer.parseInt(br.readLine());
            list.add(a);
        }

        list.sort((a, b) -> b - a);

        int maxVal = 0;
        for(int i=0; i<N; ++i) {
            int temp = (i+1) * list.get(i);
            maxVal = Math.max(maxVal , temp);
        }

        System.out.println(maxVal);
    }
}
