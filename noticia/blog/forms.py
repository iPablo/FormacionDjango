from django import forms

from .models import NewsItem, Event


class NewsItemForm(forms.ModelForm):
    publish_date = forms.DateField(widget=forms.SelectDateWidget())

    class Meta:
        model = NewsItem
        fields = ('title', 'description', 'image', 'publish_date')


class EventForm(forms.ModelForm):

    start_date = forms.DateField(widget=forms.SelectDateWidget())
    end_date = forms.DateField(widget=forms.SelectDateWidget())

    class Meta:
        model = Event
        fields = ('title', 'description', 'image', 'start_date', 'end_date')
