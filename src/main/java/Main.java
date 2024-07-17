import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        Queue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for(int i=0; i<N; ++i) {
            arr[i] = Integer.parseInt(br.readLine());
            pq.add(arr[i]);
        }

        // 최댓값 위치 찾기
        int maxIdx = -1;
        for(int i=0; i<N; ++i) {
            if(pq.peek() == arr[i]) {
                maxIdx = i; break;
            }
        }

        int cnt = 0;
        for(int i=maxIdx; i>=0; i--) {
            if(!pq.isEmpty() && pq.peek() == arr[i]) {
                pq.poll(); cnt++;
            }
        }


        System.out.println(N - cnt);
    }
}
