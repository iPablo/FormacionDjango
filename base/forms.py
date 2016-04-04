from django import forms
from .models import NewsItem, Event
from django.utils import timezone


class NewsForm(forms.ModelForm):
    title = forms.CharField()
    publish_date = timezone.now()

    class Meta:
        model = NewsItem
        fields = ('title', 'description')


class EventsForm(forms.ModelForm):

    title = forms.CharField()
    start_date = forms.DateField(widget=forms.SelectDateWidget())
    end_date = forms.DateField(widget=forms.SelectDateWidget())

    def clean(self):
        if self.cleaned_data.get("start_date") > self.cleaned_data.get("end_date"):
            raise forms.ValidationError('La fecha de inicio, no puede ser posterior a la de fin!')
        return self.cleaned_data

    class Meta:
        model = Event
        fields = ('title', 'description', 'start_date', 'end_date')
