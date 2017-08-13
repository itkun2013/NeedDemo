package com.example.refresh.bean;

import java.util.List;

/**
 * Created by Shinelon on 2017/3/26.
 */
public class DouBan {
    public int count;  //10
    public int start;  //0
    public List<Detail> subjects;  //Array
    public String title;  //豆瓣电影Top250
    public int total;  //250
    public class Detail {
        public String alt; // https://movie.douban      //
        // .com/subject/1292052/
        public List<Cast> casts;     //Array
        public int collect_count;    //	1042592
        public List<Director> directors;          //Array  //
        public List<String> genres;        //Array //
        public String id;   // 1292052
        public Pic images;       //               	Object
        public String original_title;      // The Shawshank Redemption
        public Rat rating;       //              Object
        public String subtype;      //movie
        public String title;     //	肖申克的救赎
        public String year; //1994
        public class Cast {
            public String alt;
            public Avatar avatars;
            public String id;
            public String name;
            public class Avatar {
                public String large;
                public String medium;
                public String small;
            }
        }
        public class Director {
            public String alt;
            public Avatar avatars;
            public String id;
            public String name;
            public class Avatar {
                public String large;
                public String medium;
                public String small;
            }
        }
        //        public class Genre {
//              [0]	犯罪
//              [1]	剧情
//
//        }
        public class Pic {
            public String large;
            public String medium;
            public String small;
        }
        public class Rat {
            public float average;    //9.6
            public int max; // 10
            public int min; // 0
            public String stars; //  50
        }
//        public class Genre {
//            public String type;
//        }
    }
}
