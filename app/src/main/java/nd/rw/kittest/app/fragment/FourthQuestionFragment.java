package nd.rw.kittest.app.fragment;

import android.animation.Animator;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import nd.rw.kittest.MainActivity;
import nd.rw.kittest.R;

/**
 * Created by andrew on 25.03.2016.
 */
public class FourthQuestionFragment extends QuestionFragment
        implements RadioGroup.OnCheckedChangeListener, MainActivity.StateFragmentNotifier{

    public static final String ID = "FourthFragment";
    public static final String TAG = "FourthFragment";


    @Bind(R.id.btn_finishQuiz)
    public Button mUiBtnFinish;

    @Bind(R.id.tv_a)
    public TextView mUiTvAnswerA;
    @Bind(R.id.tv_b)
    public TextView mUiTvAnswerB;
    @Bind(R.id.tv_c)
    public TextView mUiTvAnswerC;


    public boolean isASelected, isBSelected, isCSelected;

    @Bind(R.id.tv_question)
    public TextView mUiTvQuestion;
    private boolean wasNotified = false;

    public static FourthQuestionFragment newInstance() {

        Bundle args = new Bundle();

        FourthQuestionFragment fragment = new FourthQuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        Log.d(TAG, "onCheckedChanged: checkedId: " + checkedId);
        responder.finished(ID, getAnswer(checkedId));
        this.animateFinishButton();
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_fourth_question, container, false);
        ButterKnife.bind(this, view);
        mUiTvAnswerA.setOnClickListener(answerListener);
        mUiTvAnswerB.setOnClickListener(answerListener);
        mUiTvAnswerC.setOnClickListener(answerListener);

        //mUiRbAnswers.setOnCheckedChangeListener(this);
        return view;
    }

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
            previouslySelectedAnwer = v;
            animateFinishButton();
        }
    };


    @Override
    public void notifyFragment() {
        if (!wasNotified){
            Log.d(TAG, "notifyFragment: animating");
            mUiTvQuestion.animate()
                    .alpha(1f)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .setDuration(1000)
                    .start();
            mUiTvAnswerA.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(600)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            mUiTvAnswerB.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(900)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            mUiTvAnswerC.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setStartDelay(1200)
                    .setDuration(500)
                    .setInterpolator(new FastOutSlowInInterpolator())
                    .start();
            mUiTvQuestion.setAlpha(1);
            mUiTvAnswerA.setAlpha(1);
            mUiTvAnswerB.setAlpha(1);
            mUiTvAnswerC.setAlpha(1);
            mUiTvQuestion.setScaleX(1);
            wasNotified = true;
        }

    }
}
