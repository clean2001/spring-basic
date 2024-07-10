import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.*;

class Main {
    static long N, G, K;
    static List<long[]> list;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Long.parseLong(st.nextToken());
        G = Long.parseLong(st.nextToken()); // 세균수 마지노선
        K = Long.parseLong(st.nextToken()); // 뺄 수 있는 재료수

        long maxB = -1;

        list = new ArrayList<>();
        long maxVal = -1;

        for(int i=0; i<N; ++i) {
            st = new StringTokenizer(br.readLine());
            long a = Long.parseLong(st.nextToken()); // 부패 속도
            long b = Long.parseLong(st.nextToken()); // 유통기한
            long c = Long.parseLong(st.nextToken()); // 중요한 재료 여부
            list.add(new long[]{a, b, c});

            maxVal = Math.max(maxVal, b);
        }


        long s = 0, e = Long.MAX_VALUE;
        long ans = -1;
        while(s <= e) {
            long mid = (s + e) / 2;

            if(can(mid)) {
                ans = mid;
                s = mid+1;
            } else {
                e = mid-1;
            }
        }

        System.out.println(ans);
    }

    static boolean can(long target) {
        List<long[]> temp = new ArrayList<>();
        for(int i=0; i<N; ++i) {
            long s = list.get(i)[0] * Math.max(1, target - list.get(i)[1]);
            temp.add(new long[] {s, list.get(i)[2]});
        }

        temp.sort((v, w) -> Long.compare(w[0], v[0])); // s가 큰걸 먼저 제외하면 좋다.

        long cnt = 0;
        long total = 0;
        for(int i=0; i<N; ++i) {
            if(cnt < K && temp.get(i)[1] == 1) {
                cnt++; continue;
            }

            total += temp.get(i)[0];
        }

//        if(total < 0) return false; // overflow

        if(total <= G) return true;
        return false;
    }
}
