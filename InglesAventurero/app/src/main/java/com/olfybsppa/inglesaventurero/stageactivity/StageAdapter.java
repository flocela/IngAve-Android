package com.olfybsppa.inglesaventurero.stageactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.olfybsppa.inglesaventurero.stageactivity.collectors.Page;

import java.util.LinkedList;
import java.util.List;

public class StageAdapter extends FragmentAdapter
                          implements ReadableRepository.Listener{

  ReadableRepository repository;

  public StageAdapter(FragmentManager fm,
                      ReadableRepository repository) {
    super(fm);
    this.repository = repository;
    this.repository.addListener(this);
  }

  @Override
  public Fragment getItem(int position) {
    Page page = repository.getPage(position);
    if (null == page)
      return null;
    return ColloquyFragment.newInstance(page, position);
  }

  @Override
  public int getCount() {
    return repository.getCount();
  }

  @Override
  public int getItemPosition(Object object) {
    ColloquyFragment fragmentFromObj = (ColloquyFragment)object;
    Page pageFromRep = repository.getPage(fragmentFromObj.getCurrPosition());
    if (null != pageFromRep && pageFromRep.getName() == fragmentFromObj.getName()) {
      return POSITION_UNCHANGED;
    }
    else {
      return POSITION_NONE;
    }
  }

  public ColloquyFrGetter getColloquyFragmentGetter () {
    return new CollFragRepository();
  }

  public class CollFragRepository implements ColloquyFrGetter{
    private CollFragRepository() {}

    public ColloquyFragment getColloquyFragment (int pageName) {
      return StageAdapter.this.getColloquyFragment(pageName);
    }

    public List<ColloquyFragment> getColloquyFragmentsGreaterThan(int pos) {
      return StageAdapter.this.getColloquyFragmentsGreaterThan(pos);
    }

    public ColloquyFragment getCurrFragment () {
      return StageAdapter.this.getCurrFragment();
    }
  }

  private ColloquyFragment getColloquyFragment(int pageName) {
    ColloquyFragment colloquyFragment = (ColloquyFragment)getCurrentPrimaryItem();
    if (null != colloquyFragment && pageName == colloquyFragment.getName()) {
      return colloquyFragment;
    }
    else {
      for (Fragment fragment :mFragments) {
        if (fragment != null &&
          ((ColloquyFragment)fragment).getName() == pageName) {
          return (ColloquyFragment)fragment;
        }
      }
    }
    return null;
  }

  private List<ColloquyFragment> getColloquyFragmentsGreaterThan(int pos) {
    LinkedList<ColloquyFragment> greaterThan = new LinkedList<>();
    for (Fragment fragment :mFragments) {
      if (fragment != null && ((ColloquyFragment)fragment).getCurrPosition() > pos) {
        greaterThan.add((ColloquyFragment)fragment);
      }
    }
    return greaterThan;
  }

  private ColloquyFragment getCurrFragment () {
    return (ColloquyFragment)getCurrentPrimaryItem();
  }
}