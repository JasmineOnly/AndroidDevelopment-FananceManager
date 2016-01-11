package cmu.finance;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import cmu.exception.UiException;

/**
 * Created by Pedro on 09/11/2015.
 * Menu fragment viewed on the MultiFragRecord Activity.
 */
public class MenuRecordFragment extends Fragment {
    private Fragment frag;
    private FragmentTransaction transaction;

    private ImageButton expense;
    private ImageButton income;

    public MenuRecordFragment(){

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        final View rootView = inflater.inflate(R.layout.menu_record_fragment_layout, container, false);

        try {
            // Default fragment
            frag = new RecordExpenseFragment();
            getFragmentManager().beginTransaction().add(R.id.container, frag).commit();

            expense = (ImageButton) rootView.findViewById(R.id.expenseMenuButton);
            income = (ImageButton) rootView.findViewById(R.id.incomeMenuButton);

            expense.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetImg();
                    expense.setImageResource(R.drawable.minus);
                    frag = new RecordExpenseFragment();
                    transaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    transaction.commit();
                }
            });

            income.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resetImg();
                    income.setImageResource(R.drawable.plus);
                    frag = new RecordIncomeFragment();
                    transaction = getFragmentManager().beginTransaction().replace(R.id.container, frag);
                    transaction.commit();
                }
            });
        } catch (Exception e){
            UiException ue = new UiException(8, "Something went wrong", getContext());
            ue.fix();
        }

        return rootView;
    }

    private void resetImg() {
        try {
            expense.setImageResource(R.drawable.minus_black);
            income.setImageResource(R.drawable.plus_black);
        }catch (Exception e){
            UiException ue = new UiException(8, "Error resetting images", getContext());
            ue.fix();
        }
    }



}
