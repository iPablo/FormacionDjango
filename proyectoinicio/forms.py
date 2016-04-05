from django import forms
from .models import NewsItem, Event


class NewsItemForm(forms.ModelForm):
    publish_date = forms.DateTimeField(widget=forms.SelectDateWidget())

    class Meta:
        model = NewsItem
        fields = ('title', 'description', 'publish_date')


class EventForm(forms.ModelForm):
    start_date = forms.DateTimeField(widget=forms.SelectDateWidget())
    end_date = forms.DateTimeField(widget=forms.SelectDateWidget())

    class Meta:
        model = Event
        fields = ('title', 'description', 'start_date', 'end_date')
