package nd.rw.kittest.app.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.R;
import nd.rw.kittest.app.Answer;

/**
 * Created by andrew on 25.03.2016.
 */
public class NinthQuestionFragment
        extends QuestionFragment {

    public static final String ID = "NinthFragment";
    private static final String TAG = "NinthFragment";

    @Bind(R.id.tv_question)
    public TextView mUiTvQuestion;

    @Bind(R.id.tv_a)
    public TextView mUiTvAnswerA;
    @Bind(R.id.tv_b)
    public TextView mUiTvAnswerB;
    @Bind(R.id.tv_c)
    public TextView mUiTvAnswerC;

    @Bind(R.id.btn_finishQuiz)
    public Button mUiBtnFinish;

    private TextView correctTvAnswer;

    public boolean wasNotified = false;

    //region Methods

    public static NinthQuestionFragment newInstance() {
        Bundle args = new Bundle();

        NinthQuestionFragment fragment = new NinthQuestionFragment();

        fragment.setArguments(args);
        return fragment;
    }

    boolean wasFinishAnimated = false;

    private void animateFinishButton(){
        if (!wasFinishAnimated){
            mUiBtnFinish.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setStartDelay(200)
                    .setListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animation) { }

                        @Override
                        public void onAnimationEnd(Animator animation) { }

                        @Override
                        public void onAnimationCancel(Animator animation) { }

                        @Override
                        public void onAnimationRepeat(Animator animation) { }
                    })
                    .start();
            wasFinishAnimated = true;
        }

    }

    //endregion Methods

    //region Fragment methods

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_9_question, container, false);
        ButterKnife.bind(this, view);

        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);

        if (wasNotified) {
            mUiTvQuestion.setAlpha(1);
            TextView tvToPronounce = null;
            switch(currentlySelectedAnswerNumber){
                case 1:
                    tvToPronounce = mUiTvAnswerA;
                    break;
                case 2:
                    tvToPronounce = mUiTvAnswerB;
                    break;
                case 3:
                    tvToPronounce = mUiTvAnswerC;
                    break;
            }
            pronounceSelectedAnswers(true, tvToPronounce);
            reanimateAnswers(mUiTvAnswerA);
            reanimateAnswers(mUiTvAnswerB);
            reanimateAnswers(mUiTvAnswerC);
            previouslySelectedAnwer = tvToPronounce;
        }
        return view;
    }

    private void pronounceSelectedAnswers(boolean wasSelected, TextView answer){
        if (answer == null) {
            Log.e(TAG, "pronounceSelectedAnswers: answer was null");

            return;
        }
        TransitionDrawable transition = (TransitionDrawable) answer.getBackground();
        if (wasSelected){
            transition.reverseTransition(0);
            answer.setTextColor(Color.WHITE);
        } else {
            transition.startTransition(0);
            answer.setTextColor(Color.BLACK);
        }
    }

    private void reanimateAnswers(TextView textView){
        textView.setAlpha(1);
        textView.setScaleX(1);
        textView.setScaleY(1);
    }

    private int currentlySelectedAnswerNumber;
    private View previouslySelectedAnwer;

    private int transitionTime = 500;

    public View.OnClickListener answerListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previouslySelectedAnwer)
                return;

            TransitionDrawable transition = (TransitionDrawable) v.getBackground();
            transition.startTransition(transitionTime);
            TextView tv = (TextView) v;
            tv.setTextColor(Color.WHITE);
            if (previouslySelectedAnwer != null) {
                transition = (TransitionDrawable) previouslySelectedAnwer.getBackground();
                transition.reverseTransition(transitionTime);
                tv = (TextView) previouslySelectedAnwer;
                tv.setTextColor(Color.BLACK);
            }


            String answer;

            if (v == mUiTvAnswerA) {
                answer = "a";
                currentlySelectedAnswerNumber = 1;
            } else if (v == mUiTvAnswerB) {
                answer = "b";
                currentlySelectedAnswerNumber = 2;
            } else if (v == mUiTvAnswerC) {
                answer = "c";
                currentlySelectedAnswerNumber = 3;
            } else {
                answer = "ERROR";
            }

            responder.finished(getPosition(), new Answer(getPosition(), answer));

            previouslySelectedAnwer = v;
        }
    };

    //endregion Fragment methods

    @Override
    public void notifyAboutEntering() {
        if (!wasNotified){
            int delay = 600;
            int delayValue = 300;
            int answerAnimationDuration = 500;
            mUiTvQuestion.animate()
                    .alpha(1f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(1000)
                    .start();
            mUiTvAnswerA.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(delay)
                    .setDuration(answerAnimationDuration)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            delay += delayValue;
            mUiTvAnswerB.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(delay)
                    .setDuration(answerAnimationDuration)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            delay += delayValue;
            mUiTvAnswerC.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(delay)
                    .setDuration(answerAnimationDuration)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            wasNotified = true;
        }
    }

    @Override
    public int getPosition() {
        return 9;
    }

}
