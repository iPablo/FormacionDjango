from django import forms
from .models import NewsItem, Event


class NewsItemForm(forms.ModelForm):

    class Meta:
        model = NewsItem
        fields = ('title', 'description', 'publish_date')


class EventForm(forms.ModelForm):

    class Meta:
        model = Event
        fields = ('title', 'description', 'start_date', 'end_date')
