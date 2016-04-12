#!/usr/bin/env python
# -*- coding: utf-8 -*-

from django import forms
from .models import NewsItem, Event, Comment


class NewsItemForm(forms.ModelForm):
    publish_date = forms.DateTimeField(widget=forms.SelectDateWidget())

    class Meta:
        model = NewsItem
        fields = ('title', 'description', 'publish_date', 'owner')


class EventForm(forms.ModelForm):
    start_date = forms.DateTimeField(widget=forms.SelectDateWidget())
    end_date = forms.DateTimeField(widget=forms.SelectDateWidget())

    class Meta:
        model = Event
        fields = ('title', 'description', 'start_date', 'end_date', 'owner')


class CommentForm(forms.ModelForm):
    class Meta:
        model = Comment
        fields = ('author', 'text')
