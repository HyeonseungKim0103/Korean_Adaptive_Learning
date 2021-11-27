package com.example.topikappv2;

public class ProblemSet {
    private String problem_id;
    private String arranged_num;
    private String section;
    private String topik_level;
    private String choice1;
    private String choice2;
    private String choice3;
    private String choice4;
    private String prob_num;
    private String question;
    private String plural_question;
    private String question_example;
    private String solution;
    private String text;
    private String answer;
    private String score;
    private String user_answer;
    private String image;
    //듣기 파일
    private String mp3;
    private String example;

    //라디오버튼
    boolean checked1;
    boolean checked2;
    boolean checked3;
    boolean checked4;

    //category 선택할 때 필요함
    private String prob_set;

    //추가
    private ArrangedNum mArrangeList;
    private  Boolean solution_flag = false;

    public ProblemSet(ArrangedNum arrayList) {
        this.problem_id = "NA";
        this.arranged_num = "9999999999";
        this.section = "NA";
        this.topik_level = "NA";
        this.prob_num = "9999999999";
        this.question = "NA";
        this.plural_question = "NA";
        this.question_example = "NA";
        this.text = "NA";
        this.choice1 = "NA";
        this.choice2 = "NA";
        this.choice3 = "NA";
        this.choice4 = "NA";
        this.answer = "NA";
        this.score = "NA";
        this.user_answer = "NA";
        this.solution = "NA";
        this.example = "NA";

        this.checked1 = false;
        this.checked2 = false;
        this.checked3 = false;
        this.checked4 = false;

        this.prob_set = "NA";
        this.image = "NA";
        this.mp3 = "NA";

        this.mArrangeList = arrayList;
    }
    public ProblemSet(Boolean solution_flag) {
        this.solution_flag = solution_flag;
        this.problem_id = "NA";
        this.arranged_num = "9999999999";
        this.section = "NA";
        this.topik_level = "NA";
        this.prob_num = "9999999999";
        this.question = "NA";
        this.plural_question = "NA";
        this.question_example = "NA";
        this.text = "NA";
        this.choice1 = "NA";
        this.choice2 = "NA";
        this.choice3 = "NA";
        this.choice4 = "NA";
        this.answer = "NA";
        this.score = "NA";
        this.user_answer = "NA";
        this.solution = "NA";
        this.example = "NA";


        this.checked1 = false;
        this.checked2 = false;
        this.checked3 = false;
        this.checked4 = false;

        this.prob_set = "NA";
        this.image = "NA";
        this.mp3 = "NA";
    }


    public ProblemSet() {
    }

    public ProblemSet(String problem_id,String arranged_num, String prob_num, String question, String plural_question, String question_example, String text, String choice1,
                      String choice2, String choice3, String choice4, String answer, String score, String user_answer, String explanation,
                      Boolean checked1, Boolean checked2, Boolean checked3, Boolean checked4, String prob_set, String image, String mp3, String topik_level,String section,String example) {
        this.problem_id = problem_id;
        this.arranged_num = arranged_num;
        this.section = section;
        this.topik_level = topik_level;
        this.prob_num = prob_num;
        this.question = question;
        this.plural_question = plural_question;
        this.question_example = question_example;
        this.text = text;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.answer = answer;
        this.score = score;
        this.user_answer = user_answer;
        this.solution = explanation;

        this.checked1 = checked1;
        this.checked2 = checked2;
        this.checked3 = checked3;
        this.checked4 = checked4;

        this.prob_set = prob_set;
        this.image = image;
        this.mp3 = mp3;
        this.example = example;

    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSection() {return section;}

    public void setSection() {this.section = section;}

    public String getTopik_level() {return topik_level;}

    public void setTopik_level() {this.topik_level = topik_level;}

    public String getProblem_id() {return problem_id;}

    public void setProblem_id() {this.problem_id = problem_id;}

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public String getProb_set() {
        return prob_set;
    }

    public void setProb_set(String prob_set) {
        this.prob_set = prob_set;
    }

    public String getArranged_num() {
        return arranged_num;
    }

    public void setArranged_num(String arranged_num) {
        this.arranged_num = arranged_num;
    }

    public boolean isChecked1() {
        return checked1;
    }

    public void setChecked1(boolean checked1) {
        this.checked1 = checked1;
    }

    public boolean isChecked2() {
        return checked2;
    }

    public void setChecked2(boolean checked2) {
        this.checked2 = checked2;
    }

    public boolean isChecked3() {
        return checked3;
    }

    public void setChecked3(boolean checked3) {
        this.checked3 = checked3;
    }

    public boolean isChecked4() {
        return checked4;
    }

    public void setChecked4(boolean checked4) {
        this.checked4 = checked4;
    }


    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getUser_answer() {
        return user_answer;
    }

    public void setUser_answer(String user_answer) {
        this.user_answer = user_answer;
    }

    public String getChoice1() {
        return choice1;
    }

    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    public String getChoice2() {
        return choice2;
    }

    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    public String getChoice3() {
        return choice3;
    }

    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    public String getChoice4() {
        return choice4;
    }

    public void setChoice4(String choice4) {
        this.choice4 = choice4;
    }

    public String getProb_num() {
        return prob_num;
    }


    public void setProb_num(String prob_num) {
        this.prob_num = prob_num;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getPlural_question() {
        return plural_question;
    }

    public void setPlural_question(String plural_question) {
        this.plural_question = plural_question;
    }


    public String getQuestion_example() {
        return question_example;
    }

    public void setQuestion_example(String question_example) {
        this.question_example = question_example;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.answer = image;
    }

    public ArrangedNum return_arrangedList(){return mArrangeList;}

    public Boolean return_solution_flag() {return solution_flag;}


}